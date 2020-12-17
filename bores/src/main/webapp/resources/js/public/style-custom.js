$(document).ready(function() {
	menuLateral();
});

function menuLateral(){
	$('.js-toggle').bind('click', function() {
		$('.js-conteudo').toggleClass('is-toggled');
		$('.js-content').toggleClass('is-toggled');
	});
}
