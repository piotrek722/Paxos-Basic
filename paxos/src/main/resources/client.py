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
        print(server, r.status_code, 'value:', r.text)


def make_propose(key, value):
    server = random.choice(servers)
    r = requests.post(server + '/proposer/propose', json={"key": key, "value": value})
    print(server, r.status_code, 'value:', value, 'succeeded:', r.text)


print('Sending one propose message')

key = 'test'
value = '2'

make_propose(key, value)
check_values(key)
