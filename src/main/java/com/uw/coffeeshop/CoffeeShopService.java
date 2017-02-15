/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uw.coffeeshop;

import data.Model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import objects.CoffeeShop;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ubuntu
 */
public class CoffeeShopService {
      static final Logger logger = Logger.getLogger(CoffeeShopService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public CoffeeShopService() {
    }
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCoffeeShop(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        CoffeeShop coffee = mapper.readValue(jobj, CoffeeShop.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            String address = coffee.getAddress();
            String name = coffee.getName();
            double rating = coffee.getRating();
            db.updateCoffeeShop(coffee);
            logger.log(Level.INFO, "update coffeeshop with name={0}", name);
            text.append("coffee shope updated with name=").append(name).append("\n");
            logger.log(Level.INFO, "update coffeeshop with address={0}", address);
            text.append("coffee shope updated with address=").append(address).append("\n");
            logger.log(Level.INFO, "update coffeeshop with rating={0}", rating);
            text.append("coffee shope updated with rating=").append(rating).append("\n");
            
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating user after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
}
