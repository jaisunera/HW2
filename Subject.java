import java.util.ArrayList;

public abstract class Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    //Method to attach an observer to the subject
    public void attach(Observer observer) {
        observers.add(observer);
    }

    //Method to detach an observer from the subject
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    //Method to notify all attached observers when there is a change in the subject
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}

