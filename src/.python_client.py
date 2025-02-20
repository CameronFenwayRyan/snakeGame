from py4j.java_gateway import JavaGateway
import torch
import random
import numpy as np
from collections import deque
from Model import Linear_QNet, QTrainer
from Helper import plot
import time
import math
import keyboard
import os

game = JavaGateway()
MAX_MEMORY = 100_000
BATCH_SIZE = 50
LR = 0.001

class Agent:

    def __init__(self):
        self.n_games = 1
        self.epsilon = 1.00
        self.gamma = 0.95
        self.memory = deque(maxlen=MAX_MEMORY) # popleft()
        self.model = Linear_QNet(14, 512, 3)
        self.trainer = QTrainer(self.model, lr=LR, gamma=self.gamma)

    def get_state(self, game):
        java_list = list(game.getState())
        return np.array(game.getState())

    def remember(self, state, action, reward, next_state, gameOver):
        self.memory.append((state, action, reward, next_state, gameOver))

    def train_long_memory(self):
        if len(self.memory) > BATCH_SIZE:
            mini_sample = random.sample(self.memory, BATCH_SIZE)
        else:
            mini_sample = self.memory
        states, actions, rewards, next_states, gameOvers = zip(*mini_sample)
        self.trainer.train_step(states, actions, rewards, next_states, gameOvers)
        #for state, action, reward, nexrt_state, gameOver in mini_sample:
        #    self.trainer.train_step(state, action, reward, next_state, gameOver)

    def train_short_memory(self, state, action, reward, next_state, gameOver):
        self.trainer.train_step(state, action, reward, next_state, gameOver)

    def get_action(self, state):
         # random moves: tradeoff exploration / exploitation
        self.epsilon = 80 - self.n_games
        final_move = [0,0,0]
        if random.randint(0, 200) < self.epsilon:
            move = random.randint(0, 2)
            final_move[move] = 1
        else:
            state0 = torch.tensor(state, dtype=torch.float)
            prediction = self.model(state0)
            move = torch.argmax(prediction).item()
            final_move[move] = 1

        return final_move
    
def get_absolute_direction(current_direction, relative_move):
    """
    Converts a relative move ([LEFT, FORWARD, RIGHT]) into an absolute direction.
    
    :param current_direction: The snake's current heading ('UP', 'DOWN', 'LEFT', 'RIGHT')
    :param relative_move: One-hot array representing the move [LEFT, FORWARD, RIGHT]
    :return: Absolute direction ('UP', 'DOWN', 'LEFT', 'RIGHT')
    """
    directions = ['UP', 'RIGHT', 'DOWN', 'LEFT']
    index = directions.index(current_direction)

    if relative_move == [1, 0, 0]:  # LEFT
        return directions[(index - 1) % 4]
    elif relative_move == [0, 1, 0]:  # FORWARD (same direction)
        return current_direction
    elif relative_move == [0, 0, 1]:  # RIGHT
        return directions[(index + 1) % 4]
    
    raise ValueError("Invalid relative move: " + str(relative_move))

def train():
    plot_scores = []
    plot_mean_scores = []
    total_score = 0
    record = 0.0
    agent = Agent()
    oldProx = 100000
    newProx = 100000
    counter = 0
    lastScore = 0
    steps_since_last_apple = 0
    while True:
        try:
            counter += 1
            # get old state
            state_old = agent.get_state(game)

            # get move
            final_move = agent.get_action(state_old)

            # Reward calculation
            reward = game.getScore() * 200

            # Proximity to apple
            state = list(game.getState())
            XProxToApple = state[12]
            YProxToApple = state[13]

            max_distance = int(game.gameSize())  # Replace with actual board size if needed
            distance_penalty = (XProxToApple + YProxToApple) / (2 * max_distance)

            # Scale proximity reward (higher when closer)
            proximity_reward = (1 - distance_penalty) * 5  # Scale factor (adjustable)
            newProx = proximity_reward
            if oldProx > newProx:
                reward -= 10
            else:
                reward += 11
            proximity_reward = oldProx

            newScore = game.getScore()

            if (newScore > lastScore):
                steps_since_last_apple = 0
            else:
                steps_since_last_apple += 1
            
            if steps_since_last_apple >= 50:
                reward -= 130

            gameOver = game.isGameOver()
            score = game.getScore()
            state_new = agent.get_state(game)
            game.executeAction(get_absolute_direction(game.getCurrentSnakeDirection(), final_move))

            # train short memory
            agent.train_short_memory(state_old, final_move, reward, state_new, gameOver)

            # remember
            agent.remember(state_old, final_move, reward, state_new, gameOver)

            if not gameOver:
                print('Game', agent.n_games, 'Score', score, 'Record:', record)      
                agent.n_games += 1
                agent.train_long_memory() 

                if score >= record:
                    record = score
                    agent.model.save()
                
                steps_since_last_apple = 0
                lastScore = 0
                oldProx = 100000
                newProx = 100000
        
                plot_scores.append(score)
                total_score += score
                mean_score = total_score / agent.n_games
                plot_mean_scores.append(mean_score)
                plot(plot_scores, plot_mean_scores, agent.n_games)    

                game.playAgain()
                time.sleep(0.01)
                if not gameOver:
                    game.playAgain()

            time.sleep(0.0001)
    
        except Exception as e:
            game.playAgian()
            time.sleep(0.1)
            continue


train()