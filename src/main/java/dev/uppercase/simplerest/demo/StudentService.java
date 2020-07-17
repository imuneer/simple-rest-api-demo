package dev.uppercase.simplerest.demo;

import dev.uppercase.simplerest.demo.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;

public class StudentService {
    private ConcurrentMap<Integer, Student> students;
    private AtomicInteger key;

    public StudentService() {
        this.students = new ConcurrentHashMap<>();
        key = new AtomicInteger();

        this.addStudent(new Student("Marisa", 10, "M"));
        this.addStudent(new Student("Ryan", 10, "A"));
        this.addStudent(new Student("John", 10, "K"));
    }

    public String findAllStudents() {
        List<Student> list = new ArrayList<>(this.students.values());
        // convert the list to json and return as string
        return this.toJson(list);
    }

    /**
     * Create student from the json payload
     *
     * @param jsonPayload
     * @return
     */
    public boolean createStudent(String jsonPayload) {
        if (jsonPayload == null) return false;

        Gson gson = new Gson();
        try {
            Student student = (Student) gson.fromJson(jsonPayload, Student.class);
            if (student != null) {
                return this.addStudent(student);
            }
        }
        catch (Exception e) {}
        return false;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////// private methods ///////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////

    private String toJson(Object list) {
        if (list == null) return null;
        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(list);
        }
        catch (Exception e) {}
        return json;
    }

    private boolean addStudent(Student student) {
        Integer id = key.incrementAndGet();
        student.setId(id);
        this.students.put(id, student);
        return true;
    }
}
