
//Validators for form fields
export function validateEmail(email = "") {
    const length = email.length;
   let emailValid = email.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
 
    let error = {validationState: "",  message:""}
    if (length > 5 && emailValid === true ) error = {validationState:'success',
    message: '' }
    else if (length <= 0) error = {validationState:'warning',
    message: "Please enter a valid email" }
  
    return error
}

export function validatePassword(password = "", password2 = ""){
    const length = password.length
     let error = {validationState: "",  message:""}
     if(password && length >= 7 && password2 === ""){
         
        error = { validationState: 'warning', message:"Please confirm your password"
    }
     }
    else if (length >= 7 && password === password2) {
        error = { validationState: 'success', message:""
        }
    }
    else if (length < 5)  error = {validationState:'warning',
    message: "The password is too short" }
    else if (length <= 0  )  error = {validationState: 'error', message:'password cannot be empty!' }
    
     return error;
}

export function validateName(name = ""){
    const length = name.length;
    let error = {validationState: "",  message:""}
    if (length > 2) error ={validationState: 'success', message:'' }
    else if (length <= 0  ) error ={validationState: 'error', message:'Name must be longer than 2 characters!' }
    return error
}

export function validateLoginPassword(password=""){
    const length = password.length
    let error = {validationState: "",  message:""}
   if (length > 7) {
    error = { validationState: 'success', message:""
        }
       }
   else if (length < 5)  error = {validationState:'warning',
   message: "The password is too short" }
   else if (length <= 0  )  error ={validationState: 'error', message:'password cannot be empty!' }
   
    return error;
}