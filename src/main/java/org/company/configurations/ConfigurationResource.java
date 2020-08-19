package org.company.configurations;

import org.company.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


@Path("/configurations")
@Produces("application/xml")
public class ConfigurationResource {

    @Context
    UriInfo uriInfo;

    @GET
    public Configurations getConfigurations() {

        List<Configuration> list = ConfigurationDB.getAllConfigurations();

        Configurations configurations = new Configurations();
        configurations.setConfigurations(list);
        configurations.setSize(list.size());

        Link link = Link.fromUri(uriInfo.getPath()).rel("uri").build();
        configurations.setLink(link);

        for (Configuration c : list) {
            Link lnk = Link.fromUri(uriInfo.getPath() + "/" + c.getId()).rel("self").build();
            c.setLink(lnk);
        }
        return configurations;
    }

    @GET
    @Path("/{id}")
    public Response getConfigurationById(@PathParam("id") Integer id) {
        Configuration config = ConfigurationDB.getConfiguration(id);

        if (config == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        } else {
            UriBuilder builder = UriBuilder.fromResource(ConfigurationResource.class)
                    .path(ConfigurationResource.class, "getConfigurationById");
            Link link = Link.fromUri(builder.build(id)).rel("self").build();
            config.setLink(link);
        }

        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(config).build();
    }

    @POST
    @Consumes("application/xml")
    public Response createConfiguration(Configuration config) {
        if (config.getContent() == null) {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("Config content not found"))
                    .build();
        }

        Integer id = ConfigurationDB.createConfiguration(config.getContent(), config.getStatus());
        Link lnk = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
        return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(lnk.getUri()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    public Response updateConfiguration(@PathParam("id") Integer id, Configuration config) {

        Configuration origConfig = ConfigurationDB.getConfiguration(id);
        if (origConfig == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }

        if (config.getContent() == null) {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("Config content not found"))
                    .build();
        }

        ConfigurationDB.updateConfiguration(id, config);
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(new Message("Config Updated Successfully")).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteConfiguration(@PathParam("id") Integer id) {

        Configuration origConfig = ConfigurationDB.getConfiguration(id);
        if (origConfig == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }

        ConfigurationDB.removeConfiguration(id);
        return Response.status(javax.ws.rs.core.Response.Status.OK).build();
    }

}