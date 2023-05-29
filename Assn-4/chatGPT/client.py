import socket


# Client side
def run_client():
    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Define the host and port
    host = "127.0.0.1"
    port = 12345

    # Connect to the server
    client_socket.connect((host, port))
    print("Connected to the server.")

    for _ in range(5):  # Run the client 5 times
        # Receive the mean time from the server
        mean_time_str = client_socket.recv(1024).decode()
        try:
            mean_time = float(mean_time_str)
            # Send the server's time back to adjust the clock
            client_socket.send(str(mean_time).encode())
            print(f"Received mean time: {mean_time}")
        except ValueError:
            print("Invalid mean time received from the server.")

    # Close the client socket
    client_socket.close()


# Run the client
if __name__ == "__main__":
    run_client()
