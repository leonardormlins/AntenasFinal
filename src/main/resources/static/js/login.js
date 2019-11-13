$(document).ready(function() {
    /* Validar se usuario não fez logout */
    var session_login = sessionStorage.getItem("sess_email_aluno");
    if(session_login != null){
				
        //window.location.href = 'principal.html';

    }
    var cadastro = document.getElementById('cadastro');
    window.onclick = function(event) {
        if (event.target == cadastro) {
            cadastro.style.display = "none";
        }
    }
});

$('#form_register').submit(function(e){  
    e.preventDefault();
  
    json = {
    		nome: $("#nome-cadastro").val(),
	        email: $("#email-cadastro").val(),
	        senha: $("#senha-cadastro").val(),
	        //Nivel em teste!!!
	        nivel: 1
    }
	jsonString = JSON.stringify(json);
    
    
    email= $("#email-cadastro").val();
    let retornoValidacao = validaEmail(email);
    if(retornoValidacao==true)
	{
	    $.post("/aluno-cadastro",jsonString,'json');
	    fechaPopupCadastro(event);
    }
    else
    {
    	alert("O cadastro só pode ser realizado com seu e-mail institucional");
    
    }
});


function validaEmail(email) {
    let texto = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
    if (texto.test(email)) {
        if (email.indexOf('@fatec.sp.gov.br', email.length - '@fatec.sp.gov.br'.length) !== -1) {
            alert('Cadastro realizado com sucesso!');
            return true;
        } else {
            alert('Use seu e-mail institucional para realizar o cadastro (seu.nome@fatec.sp.gov.br)');
            return false;
        }
    } else {
        alert('Endereço de e-mail inválido');
        return false;
    }
}




$('#form_login').submit(function(e){    
				
    e.preventDefault();
    
    var userName = $('#email-login').val().trim();
    var password = $('#senha-login').val().trim();
    
    $.post("/aluno", JSON.stringify({'email': userName, 'senha': password}), function(data){
            
        if(data.nivel){
            window.location.href = 'principal.html';
            sessionStorage.setItem("sess_email_aluno",data.email);
        } else {
            document.getElementById("erro-login").style.display = "block";
        }
            
    }, "json");
    
});


function abrePopupCadastro(event) {
    event.preventDefault();
    document.getElementById('cadastro').style.display='block';    
}

function fechaPopupCadastro(event) {
    event.preventDefault();
    document.getElementById('cadastro').style.display='none';    
}