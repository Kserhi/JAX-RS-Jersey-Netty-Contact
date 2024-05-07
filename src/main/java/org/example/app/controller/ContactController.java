package org.example.app.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.domain.contact.Contact;
import org.example.app.network.ResponseEntity;
import org.example.app.network.ResponseEntityList;
import org.example.app.service.impl.ContactService;

import java.util.Collections;
import java.util.List;

@Path("/api/v1/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactController {

    private final ContactService service = new ContactService();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Contact contact) {
        Contact createdContact = service.create(contact);
        if (createdContact != null) {
            return Response.ok(new ResponseEntity<>(201, "Created",
                            true, createdContact).toString())
                    .status(Response.Status.CREATED).build();
        } else {
            return Response.ok(new ResponseEntity<>(204, "No Content",
                            false, "Failed to create contact").toString())
                    .status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    public Response fetchAll() {
        List<Contact> list = service.fetchAll();
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id: [0-9]+}")
    public Response fetchById(@PathParam("id") Long id) {
        Contact contact = service.fetchById(id);
        if (contact != null) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contact).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            false, "Contact not found").toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Contact contact) {
        Contact updatedContact = service.update(id, contact);
        if (updatedContact != null) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, updatedContact).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            false, "Contact not found").toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response delete(@PathParam("id") Long id) {
        if (service.delete(id)) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, "Contact deleted").toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            false, "Contact not found").toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }}
