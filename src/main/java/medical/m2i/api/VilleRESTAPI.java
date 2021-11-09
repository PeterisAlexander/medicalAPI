package medical.m2i.api;


import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ville")
public class VilleRESTAPI {

    EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll() {
        List<VilleEntity> v = em.createNativeQuery("SELECT * FROM ville", VilleEntity.class).getResultList();
        return v;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public VilleEntity getOne( @PathParam("id") int id) {
        return em.find(VilleEntity.class, id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public void deleteOne(@PathParam("id") int id) {
        VilleEntity v = em.find(VilleEntity.class, id);
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.remove(v);
            tx.commit();
            System.out.println("id de la ville supprimer : " + id);
        } catch (Exception e) {
            tx.rollback();
        } finally {}
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void addVille( VilleEntity v) {
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {}
    }
}
