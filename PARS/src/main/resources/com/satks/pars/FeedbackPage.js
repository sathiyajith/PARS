function submitMessage()
{
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var message = document.getElementById("message").value;
    
    var design_rating = document.querySelector('input[name="design"]:checked').value;
    var impl_rating = document.querySelector('input[name="impl"]:checked').value;
    
    //console.log(name);
    //console.log(email);
    //console.log(message);
    //console.log(design_rating);
    //console.log(impl_rating);
    var content = `From: ${name} \nEmail: ${email} \nDesign rating: ${design_rating} \nImplementation :${impl_rating}`;
    window.open("https://mail.google.com/mail/?view=cm&fs=1&to=sathiyajith19@gmail.com");
}