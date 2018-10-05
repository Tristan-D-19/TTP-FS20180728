import React, {Component} from 'react'
import { Alert, Button, Jumbotron,  Form, Label, ControlLabel, FormGroup, FormControl, HelpBlock, Col } from 'react-bootstrap';
import { login } from '../utils/APIHelper';
import './Login.css';
import { ACCESS_TOKEN } from '../constants';
import {  Redirect} from "react-router-dom";
import {validateEmail, validateLoginPassword} from '../utils/validators';


export default class Login extends Component {
    constructor(props) {
    super(props); 
    this.state = {
    email: '',
    password: '',
    toHome: false,
    reLoad: false, 
    passwordError: {validationState:"", message:""},
    emailError: {validationState:"", message:""},
    loginError:{validationState:"",message:""}
  }
  this.onSubmit = this.onSubmit.bind(this);
  this.handleInputChange = this.handleInputChange.bind(this);
  
}

  handleInputChange = (event) => {
    event.preventDefault();   
    const target = event.target,
          value = target.type === 
            'checkbox' ? target.checked : target.value,
          name = target.name
    this.setState({
      [name]: value
    });
  }



  
   onSubmit (event) {
    event.preventDefault()
   const {password, email } = this.state;
   console.log(password);
   console.log(email);

  this.setState({emailError:validateEmail(email),
  passwordError:validateLoginPassword(password)
  })
   if (email && password) {
   login(email, password)
    .then(response => {
        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        this.props.onLogin();
        this.props.history.push("/");
    }).catch( error => {
        if(error.status === 401) {
          this.setState({validationState:"error", message:'Your Username or Password is incorrect. Please try again!'}) 

                console.log(this.state.loginError.message);
                this.setState(() => ({
                  toLogin: true
                }))
        } else {               
                let description = 'Sorry! Something went wrong. Please try again!'
                console.log(description);
                this.props.history.push("/login");
                
                
          }
      });
  }
}

handleInputChange = (event) => {
  const target = event.target,
        value = target.type === 
          'checkbox' ? target.checked : target.value,
        name = target.name
  this.setState({
    [name]: value
  });
}
  render() {

    if (this.state.toHome === true) {
      return <Redirect to='/' />
    }
    if (this.state.reLoad === true) {
      return <Redirect to='/login' />
    }
    
    return (
      <Jumbotron className="container">
        <form onSubmit={this.onSubmit}>
          <h1>Authentication</h1>
          <FormGroup row>
            <Label for="email" sm={2}>Email</Label>
            <Col sm={10}>
            <FormControl type="email" name="email" id="email" placeholder="Email"  
            onChange={e => this.setState({email:e.target.value, emailError:validateEmail(this.state.email)})} 
            validationState={this.state.emailError.validationState}
            />
            <FormControl.Feedback />
          <ControlLabel>{this.state.emailError.message}</ControlLabel>
            </Col>
        </FormGroup>

        <FormGroup row>
            <Label for="password1" sm={2}>Password</Label>
            <Col sm={10}>
            <FormControl type="password" name="password1" id="password1" placeholder="password" 
            validationState={this.state.passwordError.validationState}
            onChange={e => {
              
              this.setState({password:e.target.value, passwordError:validateLoginPassword(this.state.password)})
              console.log("password", this.state.password);
            }}/>
            </Col>
            <FormControl.Feedback />
            <ControlLabel>{this.state.passwordError.message}</ControlLabel>
        </FormGroup>
     
        <FormGroup controlId="formValidationSuccess1" validationState="success">
        <ControlLabel
        validationState={this.state.loginError.validationState} >{this.state.loginError.message}</ControlLabel>
      <FormControl type="hidden" />
    </FormGroup>
       
       
        <Button type="submit" onClick={this.onSubmit} >Login</Button>
        </form>
      </Jumbotron>
    )
  }
}