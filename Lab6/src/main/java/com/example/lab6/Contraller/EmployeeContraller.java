package com.example.lab6.Contraller;

import com.example.lab6.Api.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/vi/employee")
public class EmployeeContraller {

    ArrayList<Employee> employees = new ArrayList<>();


   @GetMapping("/getEmployees")
    public ResponseEntity getEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity addEmployees(@RequestBody @Valid Employee employee , Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));
    }

  @PutMapping("/update/{id}")
    public  ResponseEntity updatedEmployees(@PathVariable String id, @RequestBody @Valid Employee employee,Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       for (int i = 0 ; i<employees.size() ; i++){
           if(employees.get(i).getID().equalsIgnoreCase(id)){
               employees.set(i,employee);
           }
       }
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity deletedEmployees(@PathVariable @Valid String id) {

        for (int i = 0 ; i<employees.size() ; i++){
            if(employees.get(i).getID().equalsIgnoreCase(id)){
                employees.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
            }}
            return ResponseEntity.status(400).body(new ApiResponse("Employee not deleted"));
    }

      @GetMapping("/Search/{position}")
        public ResponseEntity SearchPosition(@PathVariable @Valid String position  ){
            ArrayList<Employee> employees1= new ArrayList<>();
//          if (errors.hasErrors()) {
//              String message = errors.getFieldError().getDefaultMessage();
//              return ResponseEntity.status(400).body(message);
//          }

            for (Employee employee : employees) {
                if (employee.getPosition().equalsIgnoreCase(position)){
                    employees1.add(employee);
            }}

            if (employees1.isEmpty())
                return ResponseEntity.status(400).body( " Position is not found ");
           else return ResponseEntity.status(200).body(new ApiResponse(""+employees1));


        }

    @GetMapping("/SearchAge/{minAge}/{maxAge}")
    public ResponseEntity SearchAge(@PathVariable @Valid Integer minAge, @PathVariable @Valid Integer maxAge){
        ArrayList<Employee> employees1= new ArrayList<>();
//        if (errors.hasErrors()) {
//            String message = errors.getFieldError().getDefaultMessage();
//            return ResponseEntity.status(400).body(message);
//        }
        for (Employee employee : employees) {
            if (employee.getAge()<maxAge || employee.getAge()>minAge) {
                 employees1.add(employee);
            }}
        if (employees1.isEmpty())
            return ResponseEntity.status(400).body( " not found ");
        else return ResponseEntity.status(200).body(new ApiResponse(""+employees1));

    }

        @PutMapping("/applyAnnualLeave/{id}")
    public  ResponseEntity  applyAnnualLeave(@PathVariable @Valid String id ) {
//            if (errors.hasErrors()) {
//                String message = errors.getFieldError().getDefaultMessage();
//                return ResponseEntity.status(400).body(message);
//            }
            for (int i = 0 ; i<employees.size() ; i++){
                if(employees.get(i).getID().equalsIgnoreCase(id)){
                  if (!employees.get(i).isOnLeave()){
                      if (employees.get(i).getAnnualLeave()>0){
                          employees.get(i).setAnnualLeave(employees.get(i).getAnnualLeave()-1);
                          employees.get(i).setOnLeave(true);
                          return ResponseEntity.status(200).body(new ApiResponse("Annual leave is accepted"));
                      }}}
            } return ResponseEntity.status(200).body(new ApiResponse("Annual leave is not accepted"));
    }

    @GetMapping("/Search")
    public ResponseEntity SearchNoAnnualLeave(){
        ArrayList<Employee> employees1= new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAnnualLeave()==0) {
                employees1.add(employee);
            }}
        if (employees1.isEmpty())
            return ResponseEntity.status(400).body( " not found ");
        else return ResponseEntity.status(200).body(new ApiResponse(""+employees1));
    }

    @PutMapping("/promoteEmployee/{id1}/{id2}")
    public  ResponseEntity  promoteEmployee(@PathVariable @Valid String id1 ,@PathVariable @Valid String id2 ) {
//        if (errors.hasErrors()) {
//            String message = errors.getFieldError().getDefaultMessage();
//            return ResponseEntity.status(400).body(message);
//        }
        for (int i = 0 ; i<employees.size() ; i++){
            for (int j = 0 ; j<employees.size() ; j++){
            if(employees.get(i).getID().equalsIgnoreCase(id1) && employees.get(j).getID().equalsIgnoreCase(id2)){
                if (employees.get(i).getPosition().equalsIgnoreCase("supervisor")){
                    if (employees.get(j).getAge()>=30&&employees.get(i).getAge()>=30){
                        if (!employees.get(j).isOnLeave()){
                            employees.get(j).setPosition("supervisor");
                            return ResponseEntity.status(200).body(new ApiResponse("position updated"));
                        }}}}}}
        return ResponseEntity.status(200).body(new ApiResponse("position not updated"));
   }


}
