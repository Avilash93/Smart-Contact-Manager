const toggleSidebar = () => {
	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");

	}
	else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
};
//Input file Icon cahnge before file is submitted
function readURL(input){
	console.log("Hell there");
	if(input.files && input.files[0]){
		var reader = new FileReader();
		reader.onload = function (e) {
			$('#id-update-profile-image')
			.attr('src',e.target.result)
		};
		reader.readAsDataURL(input.files[0]);
	}
}
