import React, { Component } from 'react';
import { Alert, Button, Jumbotron,  Form, Label, ControlLabel, FormGroup, FormControl, HelpBlock, Col } from 'react-bootstrap';
import './Register.css';
import { register } from '../utils/APIHelper';
import {  Redirect} from "react-router-dom";
import {validateEmail, validatePassword, validateName} from '../utils/validators';
import { Link } from 'react-router-dom';

//Register component: Registers a new user
export default class Register extends Component {

    constructor(props) {
      super(props);
      this.state = {
          name: "", password1: "", password2: "", email: "", 
          submitted: false, 
          date: "",
          loaded: false,
          toLogin: false,
          reload:false, 
          nameError: {validationState:"", message:""},
          passwordError: {validationState:"", message:""},
          emailError: {validationState:"", message:""},
          registrationError:{validationState:"",message:""}
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.validateAll = this.validateAll.bind(this);
    }

    //validate all fields simultaneously
    validateAll(){
        this.setState({
            passwordError:validatePassword(this.state.password1, this.state.password2),
            nameError:validateName(this.state.name),
            emailError:validateEmail(this.state.email)
        })
    }

    //handle form submission
     async handleSubmit(event) {
        event.preventDefault();
     
        this.validateAll()
     const { 
       name, 
        email, 
        password1,
        password2, 
      } = this.state;
    this.setState({ submitted: true });
    const password = (password1 == password2) ? password2: null;
        if (name && email && password) {
       await register(name, email, password)
        .then(response => {  
            //redirect to login   
            if(response)
            this.props.history.push('/login');           
       this.setState({toLogin:true});
        }).catch(error => {
            let description = error.message || 'Sorry! Something went wrong. Please try again!';
            this.props.history.push("/register");           
            this.setState({reload:true, registrationError:{validationState:"error", message:description} });         
        });  
    }
}
    render(){
        const  redirectToLogin = <Redirect
        to={{
    pathname: '/login',
    state: { from: this.props.location }
    }}
    />

    const  redirectToRegister = <Redirect
    to={{
pathname: '/register',
state: { from: this.props.location }
}}
/>
        if (this.state.toLogin == true) {
            return redirectToLogin
          }
          if (this.state.reLoad == true) {
            return redirectToRegister;
          } 
     return (
<Jumbotron className="container"> 
    <form onSubmit={this.handleSubmit}>
        <h2>Register</h2>
        <FormGroup row>
            <Label for="name" sm={2}>Name</Label>
            <Col sm={10}>
                <FormControl type="name" name="name" id="fullName" placeholder="Name eg. John Doe"   
                onChange={e => {
                    this.validateAll()
                    this.setState({name:e.target.value, 
                nameError:validateName(this.state.name)})} } 
                 validationState={this.state.nameError.validationState}
                />
            <FormControl.Feedback />
            <ControlLabel>{this.state.nameError.message}</ControlLabel>
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="email" sm={2}>Email</Label>
            <Col sm={10}>
                <FormControl type="email" name="email" id="email" placeholder="Email" 
                 onChange={e => {
                    this.validateAll() 
                    this.setState({email:e.target.value, emailError:validateEmail(this.state.email)})} }  
                 validationState={this.state.emailError.validationState}
                 />
                  <FormControl.Feedback />
            <ControlLabel>{this.state.emailError.message}</ControlLabel>
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="password1" sm={2}>Password</Label>
            <Col sm={10}>
                <FormControl type="password" name="password1" id="password1"
                 placeholder="password"   
                 onChange={e => {
                    this.validateAll()  
                    this.setState({password1:e.target.value, passwordError:validatePassword(this.state.password1, this.state.password2)})}}
                 validationState={this.state.passwordError.validationState}/>
                   <FormControl.Feedback />
            <ControlLabel>{this.state.passwordError.message}</ControlLabel>
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="password2" sm={2}>Confirm Password</Label>
            <Col sm={10}>
                <FormControl type="password" name="password2" id="password2"
                 placeholder="confirm password"   onChange={e => {
                    this.validateAll() 
                    this.setState({password2:e.target.value, passwordError:validatePassword(this.state.password1, this.state.password2)})}}
                 validationState={this.state.passwordError.validationState}
                 />
                   <FormControl.Feedback />
            <ControlLabel>{this.state.passwordError.message}</ControlLabel>
            </Col>
        </FormGroup>
        <FormGroup>
            <Col smOffset={2} sm={10}>
                <Button type="submit" onClick={this.handleSubmit} >Register</Button>
            </Col>
        </FormGroup>
    </form>
    Already registed? <Link to="/login">Login now!</Link>
</Jumbotron>);
    }
}