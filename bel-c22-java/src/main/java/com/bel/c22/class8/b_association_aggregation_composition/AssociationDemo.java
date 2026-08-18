package com.bel.c22.class8.b_association_aggregation_composition;

/**
 * ASSOCIATION — "uses-a" relationship
 *
 * Two objects are RELATED but completely INDEPENDENT.
 * Neither owns the other. Both can exist on their own.
 * They interact/collaborate but don't control each other's lifecycle.
 *
 * Real-world analogy: A Doctor treats a Patient.
 * - Doctor exists without Patient, Patient exists without Doctor.
 * - They interact during consultation but neither "owns" the other.
 * - A Doctor can treat many Patients, a Patient can visit many Doctors.
 *
 * KEY CHARACTERISTICS:
 * - No ownership
 * - Both objects have independent lifecycles
 * - Can be one-to-one, one-to-many, or many-to-many
 * - Weakest relationship of the three
 */
public class AssociationDemo {

    static class Doctor {
        private String name;
        private String specialization;

        Doctor(String name, String specialization) {
            this.name = name;
            this.specialization = specialization;
        }

        // Doctor USES Patient — doesn't own or contain Patient
        void consult(Patient patient) {
            System.out.printf("  Dr. %s (%s) is consulting %s%n",
                    name, specialization, patient.getName());
        }

        String getName() { return name; }
    }

    static class Patient {
        private String name;
        private int age;

        Patient(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Patient USES Doctor — doesn't own or contain Doctor
        void visitDoctor(Doctor doctor) {
            System.out.printf("  %s (age %d) is visiting Dr. %s%n",
                    name, age, doctor.getName());
        }

        String getName() { return name; }
    }

    public static void main(String[] args) {
        System.out.println("=== ASSOCIATION: 'uses-a' relationship ===\n");

        // Both exist independently
        Doctor drSmith = new Doctor("Smith", "Cardiology");
        Doctor drJones = new Doctor("Jones", "Orthopedics");
        Patient alice = new Patient("Alice", 30);
        Patient bob = new Patient("Bob", 45);

        // Many-to-many: any doctor can consult any patient
        drSmith.consult(alice);
        drSmith.consult(bob);
        drJones.consult(alice);

        System.out.println();
        alice.visitDoctor(drSmith);
        bob.visitDoctor(drJones);

        // If we remove drSmith, alice and bob still exist (and vice versa)
        System.out.println("\n  Key point: If Dr. Smith retires, patients still exist.");
        System.out.println("  If Alice moves away, doctors still exist.");
        System.out.println("  Neither controls the other's lifecycle.\n");

        System.out.println("  Other examples of association:");
        System.out.println("  - Teacher teaches Student");
        System.out.println("  - Manager manages Project");
        System.out.println("  - Customer buys Product");
    }
}
