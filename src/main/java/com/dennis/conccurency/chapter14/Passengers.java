package com.dennis.conccurency.chapter14;

public class Passengers extends Thread {
    private String passengerName;
    private String passId;
    private String idCard;
    // 共享资源
    private final FlightSecurity flightSecurity;

    public Passengers(String passengerName, String passId, String idCard, FlightSecurity flightSecurity) {
        this.passengerName = passengerName;
        this.passId = passId;
        this.idCard = idCard;
        this.flightSecurity = flightSecurity;
    }

    @Override
    public void run() {
        while (true) {
            // 不断过安检
            this.flightSecurity.pass(passengerName, idCard, passId);
        }
    }

    public static void main(String[] args) {
        FlightSecurity securityMachine = new FlightSecurity();
        Passengers passengersThread01 = new Passengers("zhangsan01", "A_passId_1101", "A_idCard_1102", securityMachine);
        Passengers passengersThread02 = new Passengers("zhangsan02", "B_passId_1101", "B_idCard_1102", securityMachine);
        Passengers passengersThread03 = new Passengers("zhangsan03", "C_passId_1101", "C_idCard_1102", securityMachine);
        Passengers passengersThread04 = new Passengers("zhangsan04", "D_passId_1101", "D_idCard_1102", securityMachine);
        passengersThread01.start();
        passengersThread02.start();
        passengersThread03.start();
        passengersThread04.start();
    }
}
