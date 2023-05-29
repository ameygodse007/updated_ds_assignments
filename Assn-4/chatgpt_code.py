import time

# Function to calculate the mean time


def calculate_mean_time(times):
    total = sum(times)
    mean_time = total / len(times)
    return mean_time

# Function to synchronize the clocks of the nodes


def synchronize_clocks(coordinator, nodes):
    # Measure the local time for each node
    local_times = []
    for node in nodes:
        local_times.append(time.time())

    # Send local times to the coordinator
    coordinator_time = coordinator.receive_local_times(local_times)

    # Adjust the clocks of the nodes based on the coordinator's time
    for node in nodes:
        node.adjust_clock(coordinator_time)

# Coordinator class


class Coordinator:
    def receive_local_times(self, local_times):
        # Calculate the mean time
        mean_time = calculate_mean_time(local_times)

        # Broadcast the mean time to all nodes
        return mean_time

# Node class


class Node:
    def __init__(self, name):
        self.name = name
        self.local_time = time.time()

    def adjust_clock(self, coordinator_time):
        # Adjust the node's clock based on the coordinator's time
        self.local_time += (coordinator_time - self.local_time)


# Example usage
if __name__ == "__main__":
    # Create a coordinator
    coordinator = Coordinator()

    # Create nodes
    nodes = [Node("Node 1"), Node("Node 2"), Node("Node 3")]

    # Synchronize the clocks
    print("the unadjusted local times for each node")
    for node in nodes:
        print(f"{node.name} - Local Time: {node.local_time}")
    synchronize_clocks(coordinator, nodes)
    # Print the adjusted local times for each node
    print("the adjusted local times for each node")
    for node in nodes:
        print(f"{node.name} - Local Time: {node.local_time}")
