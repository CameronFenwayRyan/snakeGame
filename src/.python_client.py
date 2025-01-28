import py4j;
from py4j.java_gateway import JavaGateway

# Connect to the Java Gateway
gateway = JavaGateway()

# Get the SnakeGame instance
snake_game = gateway.entry_point

# Interact with the Java code
print("Game running:", snake_game.getState())
snake_game.executeAction(Direction.RIGHT)
snake_game.reset()
gateway.close()
