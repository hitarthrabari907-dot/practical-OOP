interface Switchable {

    void on();

    void off();

    default void toggle() {
        System.out.println("Device toggled");
    }
}
class Fan implements Switchable {

    public void on() {
        System.out.println("Fan ON");
    }

    public void off() {
        System.out.println("Fan OFF");
    }

    public void toggle() {
        System.out.println("Fan toggled");
    }
}


class Light implements Switchable {

    public void on() {
        System.out.println("Light ON");
    }

    public void off() {
        System.out.println("Light OFF");
    }

    public void toggle() {
        System.out.println("Light toggled");
    }
}

interface Permission {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {

    public static void main(String[] args) {

       
        Switchable[] devices = {
                new Fan(),
                new Light()
        };

      
        for (Switchable device : devices) {
            device.toggle();
        }

      
        Permission permission1 = new Permission() {

            public boolean maySwitchOn(
                    Switchable device, int hour) {

                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println(
                "Anonymous class: "
                + permission1.maySwitchOn(devices[0], 10));


       
        Permission permission2 =
                (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println(
                "Lambda: "
                + permission2.maySwitchOn(devices[1], 23));
    }
}