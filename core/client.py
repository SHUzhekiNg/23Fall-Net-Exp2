import socket

def create_client(dest_port=8888):

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('localhost', dest_port)

    client_socket.connect(server_address)

    message = "Hello, 服务器!"
    client_socket.send(message.encode('utf-8'))

    response = client_socket.recv(1024)
    print(f"服务器响应: {response.decode('utf-8')}")

    client_socket.close()
