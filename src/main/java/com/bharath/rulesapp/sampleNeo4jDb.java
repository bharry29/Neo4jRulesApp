///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bharath.rulesapp;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Scanner;
//
//import org.neo4j.graphdb.Direction;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Label;
//import org.neo4j.graphdb.Node;
//import org.neo4j.graphdb.Relationship;
//import org.neo4j.graphdb.RelationshipType;
//import org.neo4j.graphdb.Result;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//
//
//import org.neo4j.driver.v1.*;
//
///**
// *
// * @author bharathvadlamannati
// */
//public class sampleNeo4jDb
//{
//	GraphDatabaseService gds;
//
//	public static List<V10> performDatabaseTransaction_Retailer(String pName, int bCode)
//	{
//		String productName = pName;
//		int batchCode = bCode;
//		List<V10> resList = new ArrayList<V10>();
//		V10 res = new V10();
//
//		Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
//		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "hello" ), noSSL);
//		Session session = driver.session();
//
//		StatementResult Query1 = session.run( "MATCH (r:Retailer) "
//				+ "WHERE r.Product CONTAINS {term} "
//				+ "AND r.BatchCode = {code} "
//				+ "RETURN DISTINCT r.Name, r.BatchCode, r.Address, r.ZipCode, r.Product, r.Price"
//				,Values.parameters("term", productName, "code", batchCode));
//
//		for (Record r:Query1.list())
//		{
//			res = new V10();
//			String supName = r.get("r.Name").toString();
//			String rAddress = r.get("r.Address").toString();
//			String rZipCode = r.get("r.ZipCode").toString();
//			String rBatchCode = r.get("r.BatchCode").toString();
//			String rProduct = r.get("r.Product").toString();
//			String rPrice = r.get("r.Price").toString();
//
//			res.setName(supName);
//			res.setAddress(rAddress);
//			res.setZipCode(rZipCode);
//			res.setBatchCode(rBatchCode);
//			res.setProduct(rProduct);
//			res.setPrice(rPrice);
//			resList.add(res);
//		}
//		return resList;
//	}
//
//	public static List<V10> performDatabaseTransaction_Distributor(String pName, int bCode)
//	{
//
//		String productName = pName;
//		int batchCode = bCode;
//		List<V10> resList = new ArrayList<V10>();
//		V10 res = new V10();
//
//		Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
//		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "hello" ), noSSL);
//		Session session = driver.session();
//
//		StatementResult Query2 = session.run("MATCH (d:Distributor)-[:DELIVERS]->(r:Retailer) "
//				+ "WHERE r.Product CONTAINS {term} "
//				+ "AND d.Product = r.Product "
//				+ "AND r.BatchCode = {code} "
//				+ "AND d.BatchCode = r.BatchCode "
//				+ "RETURN DISTINCT d.Name, d.Address, d.Product, d.BatchCode, d.Price", Values.parameters("term", productName, "code", batchCode));
//
//
//		for (Record r:Query2.list())
//		{
//			res = new V10();
//			String supName = r.get("d.Name").toString();
//			String rAddress = r.get("d.Address").toString();
//			String rProduct = r.get("d.Product").toString();
//			String rBatchCode = r.get("d.BatchCode").toString();
//			String rPrice = r.get("d.Price").toString();
//
//			res.setName(supName);
//			res.setAddress(rAddress);
//			res.setBatchCode(rBatchCode);
//			res.setProduct(rProduct);
//			res.setPrice(rPrice);
//			resList.add(res);
//		}
//		return resList;
//	}
//
//	public static List<V10> performDatabaseTransaction_Factory(String pName, int bCode)
//	{
//
//		String productName = pName;
//		int batchCode = bCode;
//		List<V10> resList = new ArrayList<V10>();
//		V10 res = new V10();
//
//		Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
//		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "hello" ), noSSL);
//		Session session = driver.session();
//
//		StatementResult Query3 = session.run("MATCH (f:Factory)-[:DELIVERS] -> (d:Distributor)-[:DELIVERS]->(r:Retailer)"
//				+ "WHERE r.Product CONTAINS {term} "
//				+ "AND r.BatchCode = {code} "
//				+ "RETURN DISTINCT f.Name, f.Address, f.BatchCode, f.Product, f.Price", Values.parameters("term", productName, "code", batchCode));
//
//
//		for (Record r:Query3.list())
//		{
//			res = new V10();
//			String supName = r.get("f.Name").toString();
//			String rAddress = r.get("f.Address").toString();
//			String rBatchCode = r.get("f.BatchCode").toString();
//			String rProduct = r.get("f.Product").toString();
//			String rPrice = r.get("f.Price").toString();
//
//			res.setName(supName);
//			res.setAddress(rAddress);
//			res.setBatchCode(rBatchCode);
//			res.setProduct(rProduct);
//			res.setPrice(rPrice);
//			resList.add(res);
//		}
//		return resList;
//	}
//
//	public static List<V10> performDatabaseTransaction_Supplier2(String pName, int bCode)
//	{
//
//		String productName = pName;
//		int batchCode = bCode;
//		List<V10> resList = new ArrayList<V10>();
//		V10 res = new V10();
//
//		Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
//		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "hello" ), noSSL);
//		Session session = driver.session();
//
//		StatementResult Query4 = session.run("MATCH (s2:Supplier2)-[:DELIVERS]->(f:Factory)-[:DELIVERS] -> (d:Distributor)-[:DELIVERS]->(r:Retailer)"
//				+ "WHERE f.Product CONTAINS {term} "
//				+ "AND f.BatchCode = {code} "
//				+ "AND f.Item CONTAINS s2.Item "
//				+ "RETURN DISTINCT s2.Name, s2.Address, s2.Item, s2.Price", Values.parameters("term", productName, "code", batchCode));
//
//		for (Record r:Query4.list())
//		{
//			res = new V10();
//			String supName = r.get("s2.Name").toString();
//			String rAddress = r.get("s2.Address").toString();
//			String rItem = r.get("s2.Item").toString();
//			String rPrice = r.get("s2.Price").toString();
//
//			res.setName(supName);
//			res.setAddress(rAddress);
//			res.setProduct(rItem);
//			res.setPrice(rPrice);
//			resList.add(res);
//		}
//		return resList;
//	}
//
//	public static List<V10> performDatabaseTransaction_Supplier1(String pName, int bCode)
//	{
//
//		String productName = pName;
//		int batchCode = bCode;
//		List<V10> resList = new ArrayList<V10>();
//		V10 res = new V10();
//
//		Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
//		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "hello" ), noSSL);
//		Session session = driver.session();
//	
//		/*
//		StatementResult Query5 = session.run("MATCH (s1:Supplier1) - [:DELIVERS] -> (s2:Supplier2) - [:DELIVERS] - (f:Factory) "
//				+ "WHERE f.Product CONTAINS {term} "
//				+ "AND f.BatchCode = {code} "
//				+ "AND f.Item CONTAINS s2.Item "
//				+ "RETURN DISTINCT s1.Name, s1.Resource, s1.Address, s1.Price ", Values.parameters("term", productName, "code", batchCode));
//		 */	
//
//		StatementResult Query5 = session.run("MATCH (r:Retailer{Product:{term}, BatchCode:{code}}) <- [:DELIVERS] -(d:Distributor{Product:{term}, BatchCode:{code}})<- [:DELIVERS] -(f:Factory{Product:{term}, BatchCode:{code}}) <- [:DELIVERS]- (s2:sup2)<- [:DELIVERS]-(s1:sup1) WHERE s1.Resource CONTAINS s2.RawMaterials RETURN DISTINCT s1.Name, s1.Resource, s1.Address, s1.Price",Values.parameters("term", productName, "code", batchCode));
//
//
//		for (Record r:Query5.list())
//		{
//			res = new V10();
//			String supName = r.get("s1.Name").toString();
//			String rResource= r.get("s1.Resource").toString();
//			String rAddress = r.get("s1.Address").toString();
//			String rPrice = r.get("s1.Price").toString();
//
//			res.setName(supName);
//			res.setProduct(rResource);
//			res.setAddress(rAddress);
//			res.setPrice(rPrice);
//			resList.add(res);
//		}
//		return resList;
//	}
//}
