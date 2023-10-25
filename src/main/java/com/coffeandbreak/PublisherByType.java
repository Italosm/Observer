package com.coffeandbreak;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface SubscriberInterface {
    void update(String message);

}

interface ObservableInterface {
    void addObserver(SubscriberInterface subscriber, String notificationType);
    void removeObserver(SubscriberInterface subscriber, String notificationType);
    void notifyObservers(String message, String notificationType);
    void setMessage(String message, String notificationType);

}
class PublisherByType implements ObservableInterface{
    private final Map<String, List<SubscriberInterface>> subscribersByType = new HashMap<>();
    @Override
    public void addObserver(SubscriberInterface subscriber, String notificationType) {
        subscribersByType.computeIfAbsent(notificationType, k -> new ArrayList<>()).add(subscriber);
    }
    @Override
    public void removeObserver(SubscriberInterface subscriber, String notificationType) {
        subscribersByType.getOrDefault(notificationType, new ArrayList<>()).remove(subscriber);
    }
    @Override
    public void setMessage(String message, String notificationType) {
        notifyObservers(notificationType, message);
    }

    @Override
    public void notifyObservers(String notificationType, String message) {
        List<SubscriberInterface> subscribers = subscribersByType.getOrDefault(notificationType, new ArrayList<>());
        for (SubscriberInterface  subscriber : subscribers) {
            subscriber.update(message);
        }
    }
}


// Implementação de um observador concreto
class ConcreteSubscriber implements SubscriberInterface {
    private final String name;

    public ConcreteSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " recebeu a mensagem: " + message);
    }
}


class ObserverByType {
    public static void main(String[] args) {
        // Criando o objeto observado
        PublisherByType subject = new PublisherByType();

        // Criando observadores
        ConcreteSubscriber observer1 = new ConcreteSubscriber("Observador 1");
        ConcreteSubscriber observer2 = new ConcreteSubscriber("Observador 2");

        // Inscrevendo observadores para diferentes tipos de notificação
        subject.addObserver(observer1, "Nova Edição");
        subject.addObserver(observer2, "Nova Edição");
        subject.addObserver(observer2, "Sale");

        // Definindo mensagens e notificando observadores
        subject.setMessage("Uma nova edição está disponível para você.", "Nova Edição");
        subject.setMessage("Adquira agora a mais nova edição com 50% de desconto.", "Sale");

        subject.removeObserver(observer2, "Nova Edição");
        subject.setMessage("Uma nova edição está disponível para você.", "Nova Edição");
    }
}