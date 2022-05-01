
# What do we need ?
 We need a message queue system with calable high-throughput  and high availability
 Something that implements the competing consumer's pattern. We dont need a normal 
 pub sub system, because in the pub sub system the message is sent to all subscribers.
 In the other hand we need a system that is able to queue the messages and deliver it to 
 one consumer only in a round-robin fashion ( this is known as the competing consumers pattern) 
 when there are multiple consumers subscribed. In sum,We want to distribute the messages of the queue storage
 across several consumers in a round-robin fashion.
 97 % of our trades are new trades and 3 % are update trades

# References
1. https://www.enterpriseintegrationpatterns.com/patterns/messaging/CompetingConsumers.html

# RabbitMQ
1. Using Work Queues from RabbitMQ
2. Competing Consumers on point-to-point channel
3. Single Queue with work to do and multiple consumer to do it that will receive
  the work in a round-robin fashion

## RabbitMQ References
1. https://gist.github.com/Dev-Dipesh/4ed56529cc12eb1685ad1246c715f312
2. https://www.youtube.com/watch?v=o8eU5WiO8fw
3. https://www.rabbitmq.com/tutorials/tutorial-two-java.html
4. https://www.youtube.com/watch?v=7rkeORD4jSw&t=2s

## Advantages
1. Is a persistent Message Broker
2. Management Web UI - It has a management web UI that is able to
   1. publish messages to queue
   2. get messages from queue
   3. move messages to another queue
   4. purge queue
   5. delete queue
3. REST API - It as a REST API for the management operations
4. Security - Supports LDAP and TLS for authentication and authorization
5. ACKS - Message stays in the queue until the consumer let the broke know it was received

### Benchmark
1. I was able to put 50M trades into 1 RabbitMQ Queue on a docker instance with default configuration
2. I could read from 1 queue with a single consumer at the rate of 118M trades per hour. Reads depend on disk read speed

