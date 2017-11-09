import requests
import random

servers = ['https://paxos-1.herokuapp.com',
           'https://paxos-2.herokuapp.com',
           'https://paxos-3.herokuapp.com',
           'https://paxos-4.herokuapp.com',
           'https://paxos-5.herokuapp.com']


def check_values(key):
    print('Checking values...')
    for server in servers:
        r = requests.get(server + '/listener/' + key)
        print(server, r.status_code, r.text)


print('Sending one propose message')

key = 'test'
value = '2'

server = random.choice(servers)
r = requests.post(server + '/proposer/propose', json={"key": key, "value": value})
print(server, r.status_code)
check_values(key)


print('Sending propose messages with different values')

key = 'test2'
value1 = '2'
value2 = '4'

server1 = random.choice(servers)
server2 = random.choice(servers)

r1 = requests.post(server + '/proposer/propose', json={"key": key, "value": value1})
r2 = requests.post(server + '/proposer/propose', json={"key": key, "value": value2})

print(server1, r1.status_code)
print(server2, r2.status_code)

check_values(key)
