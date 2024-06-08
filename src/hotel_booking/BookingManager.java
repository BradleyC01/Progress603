/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.util.List;

/**
 *
 * @author shaikasif
 */
public interface BookingManager {
 
    String getContactDetails();  
    
    String getServiceDetails(List<Service> services); 
    
    List<Service> getServices(); 
    
   String getFullName();
      
}
