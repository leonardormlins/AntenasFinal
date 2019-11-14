
package hello;

import static spark.Spark.*;

import org.bson.Document;

import com.mongodb.client.FindIterable;
//import org.json.me;


public class MainServer {

	final static Model model = new Model();
	
    public static void main(String[] args) {

		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 9191;
        }
        port(port);

		//Servir conteudo html, css e javascript
		staticFileLocation("/static");

		inicializarPesquisa();
 
		REST controller = new REST(model);

		controller.inserirAluno();
	    controller.search();
	    controller.loginAluno();
	    controller.projetos();
	    controller.atribuirProjeto();
	    controller.entregaProjeto();
	    model.addAluno(Document.parse("{'email':'leo@fatec.sp.gov.br','name':'Leo', 'senha':'12345', 'nivel':'1'}"));
    }
    
    
    public static void inicializarPesquisa(){
    	model.addProjeto(Document.parse(
    		"{"
    			+ "'_id':'1234',"
    			+ "'chave':'ha86sh1'"
    			+ "'titulo':'Programa sempre teste!',"
    			+ "'descricao-breve':'Aplicacao que verifica qualitativamente os testes de um time',"
    			+ "'link-externo-1':'',"
    			+ "'link-externo-2':'',"
    			+ "'descricao-completa':'Precisa-se de uma aplicacao que analise todos os testes de um time de desenvolvimento e apresente em um Dashboard',"
    			+ "'descricao-tecnologias':'Analisa submissões de teste JUnit',"
    			+ "'fase': '4',"
    			+ "'reuniao': {"
    				+ "'data': '15/03/2020',"
    				+ "'horario':'15:00',"
    				+ "'local': 'São José dos Campos',"
    				+ "'datas-possiveis': ["
    					+ "{"
    						+ "'data': '02/03/2020',"
    						+ "'hora': '13:50'"
    					+ "}"
    				+ "]"
    			+ "}"
    			+ "'status': {"
    				+ "'negado': false,"
    				+ "'motivo': ''"
    			+ "}"
    			+ "'entregas': ["
    				+ "{"
    					+ "'aluno-responsavel': '',"
    					+ "'alunos': [],"
    					+ "'link-repositorio':'',"
    					+ "'link-cloud':'',"
    					+ "'comentario':''"
    				+ "}"
    			+ "],"
    			+ "'responsavel-cadi': 'arakaki@fatec.sp.gov.br',"
    			+ "'responsavel-professor': ["
    				+ "'giuliano@fatec.sp.gov.br'"
    			+ "],"
    			+ "'responsavel-empresario': 'Bill Clever',"
    			+ "'alunos': ["
    				+ "'leo@fatec.sp.gov.br'"
    			+ "]"
    		+ "}"));
    	
    	
    	//model.addProjeto(Document.parse("{'_id':'4321','nome':'Codigo elegante', 'fase':'1', 'responsavel-aluno': '','responsavel-cadi':'joao@email.com'}"));
		//model.addProjeto(Document.parse("{'_id': '2', 'nome':'Integra-me', 'fase':'1', 'responsavel-aluno': '','responsavel-cadi':''}"));
    }
}
