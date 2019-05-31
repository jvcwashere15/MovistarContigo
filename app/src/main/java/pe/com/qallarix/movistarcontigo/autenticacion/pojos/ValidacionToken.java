package pe.com.qallarix.movistarcontigo.autenticacion.pojos;


import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ValidacionToken {
    Message message;
    Employee employee;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
