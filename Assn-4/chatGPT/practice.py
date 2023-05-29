import socket
import time


def calculate_mean_time(times):
    t_sum = sum(times)
    asum = t_sum / len(times)
    return asum


def synchronize_clock(client_time):
    local = []
    for i in client_time:
        locals.append(time.time())

    mean_t = calculate_mean_time(local)

    for i, client in enumerate(client_time):
        client.send(str(mean_t).encode())
        c_time = float(client.recv(1024).decode())
        m_time = local[i] + (c_time - m_time)
        print(f"Client {i+1} - Local Time {m_time}")


def run_ser():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    host = "127.0.0.1"
    port = 12345

    server_socket.connect((host, port))

    print("Server Connected")
