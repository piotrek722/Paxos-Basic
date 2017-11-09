import requests
import random
import threading
from threading import Thread

servers = ['https://paxos-1.herokuapp.com',
           'https://paxos-2.herokuapp.com',
           'https://paxos-3.herokuapp.com',
           'https://paxos-4.herokuapp.com',
           'https://paxos-5.herokuapp.com']


def check_values(key):
    print('Checking values...')
    for server in servers:
        r = requests.get(server + '/listener/' + key)
        print(server, r.status_code, 'value:', r.text)


def make_propose(key, value):
    server = random.choice(servers)
    r = requests.post(server + '/proposer/propose', json={"key": key, "value": value})
    print(server, r.status_code, 'value:', value, 'succeeded:', r.text)


print('Sending propose messages with different values')

key = 'test'
value1 = '3'
value2 = '4'
# value3 = '3'

Thread(target=make_propose(key, value1)).start()
Thread(target=make_propose(key, value2)).start()
# Thread(target=make_propose(key, value3)).start()

check_values(key)
