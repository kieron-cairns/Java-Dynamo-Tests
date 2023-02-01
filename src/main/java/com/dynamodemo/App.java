package com.dynamodemo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import java.util.HashMap;

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

        Item item = new Item().withPrimaryKey("Name", "Hello World 3")
        .withString("Date", "John Doe");
        // .withNumber("Age", 30);
        
        table.putItem(item);   
    }

    public void  UpdateTableItem() {
        
    }

    public static void main( String[] args )
    {
        System.out.println("Main Mehtod Hit");

        App app = new App();

        app.AddTableItem();
    }
}
