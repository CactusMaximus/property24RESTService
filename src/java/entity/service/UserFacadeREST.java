/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Property;
import entity.User;
import java.io.File;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author rss
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "property24RESTServicePU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }
    
    public void registerUser(String username, String password, String cell, String email, String isAdmin){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
        EntityManager em = factory.createEntityManager();
        
         Query q = em.createQuery("INSERT INTO u USER VALUES(:username,:email,:password,:cell,:isAdmin)");
      
        q.setParameter("username", username);
        q.setParameter("password", password);
        q.setParameter("email",email);
        q.setParameter("cell",cell);
        q.setParameter("isAdmin",isAdmin);
      
        
        //User userResult = (User) q.getSingleResult();
      
        int result = q.executeUpdate();
        System.out.print(result);     
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
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
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
    public User find(@PathParam("id") Integer id) {
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
    @Path("image/avatar")
    @Produces("image/*")
    public Response getAvatar(@QueryParam("avatarNum") String avatarNum){
        
        String url = "/users/rss/Downloads/avatarMan.jpg";
        
        System.out.println("*************** " + avatarNum);
//        switch(avatarNum){
//           case "1":
//               url = "/users/rss/Downloads/avatarMan.jpg";
//           case "2":
//             url = "/users/rss/Downloads/avatarMan.jpg";
//           case "3":
//               url = "/users/rss/Downloads/avatarGirl.jpg";
//          case "4":
//                url = "/users/rss/Downloads/avatarMan1.jpg";
//           default:
//               url = "/users/rss/Downloads/1.jpg";
//       }
        
        File f = new File(url);
        String mt = new MimetypesFileTypeMap().getContentType(f);
        return Response.ok(f,mt).build();
    }
    
    

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("register")
    @Produces({MediaType.APPLICATION_JSON})
    public User register(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("cellNumber")String cell,
            @QueryParam("email")String email,
            @QueryParam("isAdmin") String isAdmin){
            
        User newUser = new User(username,email,password,isAdmin,new Integer(cell));
    
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("property24RESTServicePU");
        EntityManager em = factory.createEntityManager();
        
        try{
            this.create(newUser);
        }catch(Exception e){
            System.out.println(e);
        }     
        return newUser;
    }

    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
