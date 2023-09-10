import socket

def create_server(port=8888):

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # UDP: SOCK_DGRAM
    server_address = ('localhost', port)

    server_socket.bind(server_address)
    server_socket.listen(5)

    print("等待客户端连接...")

    while True:

        client_socket, client_address = server_socket.accept()
        
        print(f"接受来自{client_address}的连接")
        
        data = client_socket.recv(1024)
        if not data:
            break

        print(f"接收到的数据: {data.decode('utf-8')}")
        

        response = "Hello, 客户端!"
        client_socket.send(response.encode('utf-8'))

        client_socket.close()

    server_socket.close()
