package testRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions
  (
  features={".//Features/"},		  // this is to run all available features in Features folder
  //features={".//Features/Customers.feature"},  // this statement is to run only one feature in Features folder ex:Customers feature
  //features= {".//Features/login.feature",".//Features/Customers.feature"},// this is to run for only required features out of all available features
   glue="stepDefinitions",
   dryRun=false,
   monochrome=true,
   plugin= {"pretty","html:test-output"},
   //tags= {"@sanity"}, // to execute only one type of scenarios sanity
   //tags= {"@sanity", "@regression"}, // this is to Execute Sanity and Regression 
   tags= {"@sanity, @regression"} // to execute either sanity or regression type of scenarios
  )
public class TestRun {

}
