package eu.hopu;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v2") // set the path to REST web services
public class ApplicationConfig extends Application {}