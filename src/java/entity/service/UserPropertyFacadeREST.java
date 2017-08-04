/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Property;
import entity.User;
import entity.UserProperty;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rss
 */
@Stateless
@Path("entity.userProperty")
public class UserPropertyFacadeREST extends AbstractFacade<UserProperty> {

    @EJB
    private PropertyFacadeREST propertyFacadeREST;

    @PersistenceContext(unitName = "property24RESTServicePU")
    private EntityManager em;

    public UserPropertyFacadeREST() {
        super(UserProperty.class);
    }
    
    
    
    public List<User> validateLogin(String username, String password){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
        EntityManager em = factory.createEntityManager();
      
        
         Query q = em.createQuery("SELECT u FROM User u WHERE u.userName = :username "
         +"AND u.userPassword = :password");
      
        q.setParameter("username", username);
        q.setParameter("password", password);
      
        
        //User userResult = (User) q.getSingleResult();
      
        if(q.getResultList().isEmpty()){
            return null;
        }else{
            List<User> userResult = q.getResultList();
            return userResult;
        }
    }
    
    
    public List<Property> findWhere(Boolean toLet, Integer bedrooms, Integer bathrooms, Integer minPrice, Integer maxPrice){
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
        EntityManager em = factory.createEntityManager();
      
        
        System.out.println("HERE");
        Query q = em.createQuery("SELECT p FROM Property p WHERE p.price >= :minPrice "
                          + "AND p.price <= :maxPrice "
                          +"AND p.bedrooms >= :bedrooms "
                          +"AND p.bathrooms >= :bathrooms "
                          +"AND p.toLet = :toLet");
        
      
        q.setParameter("minPrice", minPrice);
        q.setParameter("maxPrice", maxPrice);
        q.setParameter("bedrooms", bedrooms);
        q.setParameter("bathrooms", bathrooms);
        q.setParameter("toLet", toLet);
        
        List<Property> propList = q.getResultList();
        
        return propList;

    }

   
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserProperty entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, UserProperty entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserProperty find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Property> searchProperties(@QueryParam("toLet") Boolean toLet,
            @QueryParam("bedrooms")Integer bedrooms,
            @QueryParam("bathrooms")Integer bathrooms,
            @QueryParam("minPrice")Integer minPrice,
            @QueryParam("maxPrice")Integer maxPrice) {
        
        return findWhere(toLet,bedrooms,bathrooms,minPrice,maxPrice);
    }
    
    @GET
    @Path("addToFavourites")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserProperty addPropertyToFavourites(@QueryParam("propertyId")String propertyId,
            @QueryParam("userId")String userId){
        
       EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
       EntityManager em = factory.createEntityManager();
        
        UserProperty up = new UserProperty(propertyId,userId);
        try{
             create(up);
             em.flush();
        }catch(Exception e){
            System.out.println("Exception:" + e);
        }
        return up;
    }
    
    //@GET
   // @Path("favourites")
   // @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserProperty> getFavourites(String userId){
        
       EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
       EntityManager em = factory.createEntityManager();
        //find all records in userProperty
        
        Query findUserProperties = em.createQuery("SELECT up FROM UserProperty up WHERE up.userId = :userId");
        findUserProperties.setParameter("userId",Integer.valueOf(userId));
        
        List<UserProperty> uplist = findUserProperties.getResultList();
        
        
        return uplist;
        
    }

 
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserProperty> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserProperty> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("login")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> login(@QueryParam("username") String username,
            @QueryParam("password") String password){
        return validateLogin(username,password);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
