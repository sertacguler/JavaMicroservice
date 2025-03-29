const { Kafka } = require('kafkajs');

// Kafka bağlantısı için yapılandırma
const kafka = new Kafka({
  clientId: 'multi-topic-consumer',
  brokers: ['localhost:9092'], // Kafka broker adresi
});

// Dinlenecek topic'ler
const topics = ['order-created', 'order-failed', 'payment-failed','payment-processed','stock-reduced'];
const groupId = 'multi-topic-group'; // Consumer grubu

const run = async () => {
  // Consumer oluşturma
  const consumer = kafka.consumer({ groupId });

  // Consumer'ı başlat
  await consumer.connect();

  // Tüm topic'lere abone ol
  for (const topic of topics) {
    await consumer.subscribe({ topic, fromBeginning: true });
  }

  console.log(`Consumer is listening to topics: ${topics.join(', ')}`);

  // Mesajları dinle
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log(`[${topic}] -> Partition: ${partition}, Offset: ${message.offset}, Value: ${message.value.toString()}`);
    },
  });
};

// Hata yakalama
run().catch(console.error);
