package anyapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.gigaspaces.anyapi.Programmer;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.executor.Task;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.client.SQLQuery;

public class Main {

	public static void main(String[] args) throws Exception{

		final GigaSpace gigaSpace = new GigaSpaceConfigurer(
				new UrlSpaceConfigurer("/./ca").create()).create();


		/******************* Pojo *******************************/
		gigaSpace.write(new Programmer(123, "Me", "groovy"));
		gigaSpace.write(new Programmer(345, "you", "java"));

		Programmer programmer = gigaSpace.read(new Programmer(123));
		System.out.println(programmer);


		/****************** Document ***************************/
		SpaceDocument document = new SpaceDocument("anyapi.Programmer");

		document.setProperty("id", 345);
		SpaceDocument programmerDoc = gigaSpace.read(document);

		System.out.println(programmerDoc);

		document.setProperty("id", 789);
		document.setProperty("name", "he");
		document.setProperty("age", 21);
		gigaSpace.write(document);


		/****************** SQLQuery ***************************/
		programmer = gigaSpace.read(new SQLQuery<Programmer>(Programmer.class, 
		"id=789 AND name='he'"));
		System.out.println(programmer);


		/****************** JDBC *******************************/
		Class.forName("com.j_spaces.jdbc.driver.GDriver");
		Connection connection = DriverManager.getConnection("jdbc:gigaspaces:url:jini://localhost/*/ca");
		Statement statement = connection.createStatement();

		statement.execute("SELECT * FROM anyapi.Programmer WHERE age=21");

		ResultSet resultSet = statement.getResultSet();
		while(resultSet.next())
			System.out.println("JDBC: id=" + resultSet.getInt("id") + " name="+ resultSet.getString("name") + " age=" + resultSet.getInt("age"));


		/****************** JPA ********************************/
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("anyapi", null);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		programmer = entityManager.find(Programmer.class, 345);
		System.out.println(programmer);

		@SuppressWarnings({"JpaQlInspection"})
        TypedQuery<Programmer> query = entityManager.createQuery(
				"SELECT p FROM com.gigaspaces.anyapi.Programmer p WHERE p.id=123", Programmer.class);

		List<Programmer> resultList = query.getResultList();
		System.out.println(resultList.get(0));


		/******************** Executor (Map-Reduce) *****************/
		Future<Integer> count = gigaSpace.execute( new Task<Integer>() {

			@Override
			public Integer execute() throws Exception {

				int totalId = 0;
				Programmer[] programmers = 
					gigaSpace.readMultiple(new Programmer(), Integer.MAX_VALUE);

				for(Programmer prog : programmers){
					totalId += prog.getId();
				}
				return totalId;
			}
		});

		System.out.println(count.get());
	}
}
