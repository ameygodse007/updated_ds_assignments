import time
import socket


# Function to calculate the mean time
def calculate_mean_time(times):
    total = sum(times)
    mean_time = total / len(times)
    return mean_time


# Function to synchronize the clocks of the clients
def synchronize_clocks(client_sockets):
    # Measure the local time for each client
    local_times = []
    for client_socket in client_sockets:
        local_times.append(time.time())

    # Send local times to the clients
    mean_time = calculate_mean_time(local_times)
    for i, client_socket in enumerate(client_sockets):
        client_socket.send(str(mean_time).encode())

        # Adjust the clock of the client based on the server's time
        server_time = float(client_socket.recv(1024).decode())
        client_time = local_times[i] + (server_time - mean_time)
        print(f"Client {i+1} - Local Time: {client_time}")


# Server side
def run_server():
    # Create a socket object
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Define the host and port
    host = "127.0.0.1"
    port = 12345

    # Bind the socket to the host and port
    server_socket.bind((host, port))

    # Listen for incoming connections
    server_socket.listen(3)
    print("Server is listening...")

    # Accept client connections
    client_sockets = []
    for i in range(3):
        client_socket, address = server_socket.accept()
        print(f"Connection established with Client {i+1}")
        client_sockets.append(client_socket)
        # Synchronize the clocks
        synchronize_clocks(client_sockets)

    # Close the server socket
    server_socket.close()


# Run the server
if __name__ == "__main__":
    run_server()
