package com.dynamodemo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

/**
 * Hello world!
 *
 */
public class App 
{

    public void CreateATable()
    {
        CreateTableRequest request = new CreateTableRequest()
            .withAttributeDefinitions(new AttributeDefinition( "Name", ScalarAttributeType.S))
            .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
            .withProvisionedThroughput(new ProvisionedThroughput(
                new Long(10), new Long(10)))
                    .withTableName("hello_table");
        
        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        try {
            CreateTableResult result = ddb.createTable(request);
            System.out.println(result.getTableDescription().getTableName());
        } 
        catch (Exception e) {
            System.err.println("There has been an error");
            System.exit(1);
        }
    }

    public void AddTableItem() {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("hello_table");

        Item item = new Item().withPrimaryKey("Name", "HelloWorld4")
        .withString("Date", "John Doe");
        // .withNumber("Age", 30);
        
        table.putItem(item);   
    }

    public void  UpdateTableItem() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("hello_table");

        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        expressionAttributeNames.put("#D", "Date");
       
        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());


        // expressionAttributeValues.put(":date", "02/02/2022");

        AttributeUpdate test = new AttributeUpdate("Date").put(timeStamp);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Name", "HelloWorld4").withAttributeUpdate(test)
        .withReturnValues(ReturnValue.ALL_NEW);
        // UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

        table.updateItem(updateItemSpec);
    }

    public void ReadTable() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("hello_table");

        Item item = table.getItem("Name", "HelloWorld4");

        System.out.println("Date: " + item.getString("Date"));
    }

    public String Test() {
        // System.out.println("Test");

        // new Timer().scheduleAtFixedRate(new TimerTask() {
        //     @Override
        //     public void run () {
        //         System.out.println("Table item updated");
        //         App app = new App();
        //         app.UpdateTableItem();

        //     }
        // }, 0, 5000);
            return "Hello From Test Method v2";
        
    }

    public static void main( String[] args )
    {
        System.out.println("Main Mehtod Hit");



        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                System.out.println("Table item updated");
                App app = new App();
                app.UpdateTableItem();

            }
        }, 0, 5000);

        // app.UpdateTableItem();
    }
}
