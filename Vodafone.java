`@PostMapping("/create-consumer-group")`
`public String createConsumerGroup(@RequestParam String groupId, @RequestParam String topic) {`
`// Create a new consumer configuration with the specified group ID`
`Map<String, Object> consumerProps = new HashMap<>();`
`consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");`
`consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);`
`consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);`
`consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);`
`consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");`

`// Create a new consumer factory with the new configuration`
`ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);`

`// Create container properties for the topic`
`ContainerProperties containerProps = new ContainerProperties(topic);`
`containerProps.setMessageListener((MessageListener<String, String>) record -> {`
`System.out.println("Received message: " + record.value());`
`});`

`// Create a new Kafka listener container with the consumer factory and container`
`// properties`
`ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(`
`consumerFactory, containerProps);`
`container.start();`

`return "Consumer group '" + groupId + "' created and listening to topic '" + topic + "'";`
`}`