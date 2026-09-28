// A service request made by a student (e.g. transcript, ID card)
public class ServiceRequest {
    private int requestId;
    private String studentId;
    private String description;

    public ServiceRequest(int requestId, String studentId, String description) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.description = description;
    }

    public int getRequestId() { return requestId; }
    public String getStudentId() { return studentId; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Request #" + requestId + " | Student: " + studentId + " | " + description;
    }
}
