// package lab.end2end.concert.test;

// import org.junit.AfterClass;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import lab.end2end.concert.domain.Concert;
// import lab.end2end.concert.domain.Performer;

// import javax.ws.rs.client.Client;
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.client.Entity;
// import javax.ws.rs.client.Invocation.Builder;
// import javax.ws.rs.core.GenericType;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.Assert.assertEquals;


// /**
//  * Simple JUnit testJPA to testJPA the behaviour of the Concert Web service.
//  * <p>
//  * The testJPA is implemented using the JAX-RS client API.
//  */

//  public class ConcertResourceIT {

//     private static Logger LOGGER = LoggerFactory.getLogger(ConcertResourceIT.class);

//     private static String WEB_SERVICE_URI = "http://localhost:8080/concerts";

//     private static Client client;

//     // List of Concerts.
//     private static List<Concert> concerts = new ArrayList<>();

//     // List of Concert URIs generated by the Web service. The Concert at
//     // position i in concerts has the URI at position i in concertUris.
//     private static List<Long> concertUris = new ArrayList<>();

//     @BeforeClass
//     public static void createClient() {
//         // Use ClientBuilder to create a new client that can be used to create
//         // connections to the Web service.
//         client = ClientBuilder.newClient();

//         // Create some Concerts.
//         Performer queen = new Performer("Queen", "queen.jpg");
//         concerts.add(new Concert("One Night of Queen",  LocalDateTime.of(2017, 8,
//                 4, 20, 0), queen));
//         Performer paulineBlack = new Performer("Pauline Black", "paulineBlack.jpg");
//         concerts.add(new Concert("The Selecter and the Beat", LocalDateTime.of(
//                 2018, 1, 25, 20, 0), paulineBlack));
//         Performer aliceCooper = new Performer("Alice Cooper", "aliceCooper.jpg");
//         concerts.add(new Concert("Spend the Night with Alice Cooper",
//                 LocalDateTime.of(2017, 10, 27, 19, 0), aliceCooper));
//     }

//     @AfterClass
//     public static void closeConnection() {
//         // After all tests have run, close the client.
//         client.close();
//     }

//     @Before
//     public void clearAndPopulate() {
//         // Delete all Concerts in the Web service.
//         Builder builder = client.target(WEB_SERVICE_URI).request();
//         try (Response response = builder.delete()) {
//         }

//         // Clear Uris
//         concertUris.clear();

//         // Populate the Web service with Concerts.
//         for (Concert concert : concerts) {
//             builder = client.target(WEB_SERVICE_URI).request(MediaType.APPLICATION_JSON);
//             try (Response response = builder.post(Entity.json(concert))) {
//                 LOGGER.warn("STATUS: " + response.getStatus());
//                 //String concertUri = response.getLocation().toString();
//                 //concertUris.add(concertUri);
//             }
//         }

//         builder = client.target(WEB_SERVICE_URI).request();
//         // get all concerts
//         try (Response response = builder.get()) {
//             List<Concert> concerts =  response.readEntity(new GenericType<List<Concert>>() {});
//             for (Concert concert : concerts) {
//                 concertUris.add(concert.getId());
//             }
//         }
//     }

//     @Test
//     public void testCreate() {
//         // Create a new Concert.
//         Performer performer = new Performer("Blondie", "blondie.jpg");
//         Concert concert = new Concert("Blondie", LocalDateTime.of(2017, 4, 26, 20,
//                 0), performer);

//         // Prepare an invocation on the Concert service
//         Builder builder = client.target(WEB_SERVICE_URI).request(MediaType.APPLICATION_JSON);

//         // Make the service invocation via a HTTP POST message, and wait for the response.
//         try (Response response = builder.post(Entity.json(concert))) {

//             // Check that the HTTP response code is 201 Created.
//             int responseCode = response.getStatus();
//             assertEquals(Response.Status.CREATED.getStatusCode(), responseCode);

           
//         }
//     }

//     @Test
//     public void testRetrieve() {
//         Long concertid = concertUris.get(concertUris.size() - 1);
//        // assertEquals(concertUris.get(concertUris.size() - 1), concertid+"xx");
//         // Make an invocation on a Concert URI and specify JSON as the required data format.
//         System.out.println(concertid+"############");
//         Builder builder = client.target(WEB_SERVICE_URI+"/"+concertid).request()
//                 .accept(MediaType.APPLICATION_JSON);

//         // Make the service invocation via a HTTP GET message, and wait for the response.
//         try (Response response = builder.get()) {

//             // Check that the HTTP response code is 200 OK.
//             int responseCode = response.getStatus();
//             assertEquals(Response.Status.OK.getStatusCode(), responseCode);

//             // Check that the expected Concert is returned.
//             Concert concert = response.readEntity(Concert.class);
//             assertEquals(concerts.get(concerts.size() - 1).getTitle(), concert.getTitle());
//         }
//     }

//     @Test
//     public void testUpdate() {
//         Long concertid = concertUris.get(concertUris.size() - 1);

//         // Prepare an invocation on a Concert URI.
//         Builder builder = client.target(WEB_SERVICE_URI+"/"+concertid).request()
//                 .accept(MediaType.APPLICATION_JSON);

//         // Make the service invocation via a HTTP GET message, and wait for the response.
//         Concert concert = null;
//         try (Response response = builder.get()) {

//             // Extract the Concert returned from the GET request.
//             concert = response.readEntity(Concert.class);
//         }


//         // Modify the Concert's date.
//        // LocalDateTime now = LocalDateTime.now();
//         String newTitle = "TestUpdate";
//         concert.setTitle(newTitle);

//         // Prepare an invocation on the Concerts URI.
//         builder = client.target(WEB_SERVICE_URI).request();

//         // Make the service invocation via a HTTP PUT request, supplying
//         // the updated Concert in the request message body.
//         try (Response response = builder.put(Entity.json(concert))) {

//             // Check that the PUT request was successful.
//             assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//         }

//         // Re-query the Concert, and check that its date has been modified.
//         try (Response response = client.target(WEB_SERVICE_URI+"/"+concertid).request()
//                 .accept(MediaType.APPLICATION_JSON).get()) {
//             concert = response.readEntity(Concert.class);
//             assertEquals(newTitle, concert.getTitle());
//         }
//     }

//     @Test
//     public void testDelete() {
//         Long concertid = concertUris.get(concertUris.size() - 1);

//         // Prepare an invocation on a Concert URI.
//         Builder builder = client.target(WEB_SERVICE_URI+"/"+concertid).request();

//         // Make the service invocation via a HTTP DELETE message, and wait
//         // for the response.
//         try (Response response = builder.delete()) {

//             // Check that the DELETE request was successful.
//             assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//         }
//        // assertEquals("111", concertid);
//         // Requery the Concert.
//         try (Response response = client
//                 .target(WEB_SERVICE_URI+"/"+concertid)
//                 .request()
//                 .accept(MediaType.APPLICATION_JSON)
//                 .get()) {

//             // Check that the GET request returns a 404 result.
//             assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//         }
//     }

//     @Test
//     public void testDeleteAll() {
//         // Prepare an invocation on the Concert Web service.
//         Builder builder = client.target(WEB_SERVICE_URI).request();

//         // Make the service invocation via a HTTP DELETE message, and wait
//         // for the response.
//         try (Response response = builder.delete()) {

//             // Check that the HTTP response code is 204 No content.
//             int status = response.getStatus();
//             assertEquals(Response.Status.NO_CONTENT.getStatusCode(), status);
//         }
//     }
// }