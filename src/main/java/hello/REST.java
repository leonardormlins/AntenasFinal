package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;

import spark.Request;
import spark.Response;
import spark.Route;

public class REST {

	private Model model;

	public REST(Model model) {
		super();
		this.model = model;
	}

	public void loginAluno() {
		post("/aluno", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.header("Access-Control_Allow-Origin", "*");

				Document json = Document.parse(request.body());
				try {
					Document aluno = model.login(json);
					if(aluno==null) {
						return false;
					}
					return aluno.toJson();

				} catch (NullPointerException e) {
					return null;
				}

			}
		});
	}
	public void atribuirProjeto() {
		post("/add-projeto", (Request request, Response response) -> {
			
			response.header("Access-Control-Allow-Origin", "*");
			Document json = Document.parse(request.body());
			System.out.println("test-4");
			
			try {
				Document retorno = model.updateProjeto(json);
				if(retorno!=null) return retorno;
				else return false;
				
			} catch (NullPointerException e) {
				return null;
			}
		});
	}
	public void inserirAluno() {

		post("/aluno-cadastro", (Request request, Response response) -> {

			response.header("Access-Control-Allow-Origin", "*");

			Document aluno = Document.parse(request.body());

			model.addAluno(aluno);

			return aluno.toJson();
		});
	}

	public void projetos() {
		get("/projetos", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				FindIterable<Document> projectsFound = model.listaProjetos();

				return StreamSupport.stream(projectsFound.spliterator(), false).map(Document::toJson)
						.collect(Collectors.joining(", ", "[", "]"));
			}
		});
	}

	public void search() {
		get("/search", (request, response) -> {
			return model.search(request.queryParams("chave"), request.queryParams("valor"));
		});
		
		get("/search/:chave", (request, response) -> {
			return model.buscaPorChave(request.params(":chave"));
		});
		
		get("/dono/:email", (request, response) -> {
			String ret = model.buscaPorDono(request.params(":email"));
			return ret;
		});

		get("/semdono", (request, response) -> {
			return model.buscaSemDono();
		});
		
		get("/putAluno", (request, response) -> {
			return model.atribuirAluno(request.queryParams("emailProf"), request.queryParams("_id"));
		});
		
		get("/put", (request, response) -> {
			return model.atribuir(Document.parse(request.body()));
		});

	}

	public void alterarId() {
		post("/alterarId", (req, res) -> {
			model.alterarId(req.queryParams("id"), new Document("$set", Document.parse(req.body())));
			return model.listAlunos();
		});
	}

	public void listAlunos() {
		get("/listarAlunos", (req, res) -> {
			return model.listAlunos();
		});
	}
	
	public void entregaProjeto() {
		post("/entregar", (req, res) -> {
			System.out.println("test");
			Document project = Document.parse(req.body());
			System.out.println(project);
			String id = project.getString("id");
			String alunos = project.getString("autores");
			String descricao = project.getString("descricao");
			String linkGitHub = project.getString("link");
			Document now = model.getProject(id);
			return model.submitProject(id, now, alunos, descricao, linkGitHub);
		});
	}

}
