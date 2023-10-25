package com.coffeandbreak;

import java.util.ArrayList;
import java.util.List;

interface ObserverInterface {
    void update(String message);

}

interface Subject {
    void addObserver(ObserverInterface observer);
    void removeObserver(ObserverInterface observer);
    void setMessage(String message);
    void notifyObservers();

}

class PublisherNewEdition implements Subject {
    private final List<ObserverInterface> observers = new ArrayList<>();
    private String message;

    @Override
    public void addObserver(ObserverInterface observer) {
        observers.add(observer);

    }
    @Override
    public void removeObserver(ObserverInterface observer) {
        observers.removeIf(currentObserver -> currentObserver.equals(observer));

    }

    @Override
    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (ObserverInterface observer : observers) {
            observer.update(message);
        }
    }

}

class ConcreteObserverEdition implements ObserverInterface {
    private final String name;

    public ConcreteObserverEdition(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " recebeu a mensagem: " + message);
    }
}



class ObserverPatternExample {
    public static void main(String[] args) {
        Subject subject = new PublisherNewEdition();

        ObserverInterface observer1 = new ConcreteObserverEdition("Observador 1");
        ObserverInterface observer2 = new ConcreteObserverEdition("Observador 2");

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.setMessage("Nova mensagem para observadores.");
        subject.removeObserver(observer2);

        subject.setMessage("Outra mensagem para observadores.");
    }
}

