import React, { Component } from 'react';
import { Alert, Button, Jumbotron,  Form, Label, Input, FormGroup, ControlLabel, HelpBlock, Col } from 'reactstrap';
import './Register.css';
import { register } from '../utils/APIHelper';
import {
    Route,
    Redirect
  } from "react-router-dom";

export default class Register extends Component {

    constructor(props) {
      super(props);
      this.state = {
          name: "", password1: "", password2: "", email: "", 
          submitted: false, 
          response: 0,
          date: "",
          loaded: false,
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    }


    //handle form submission
    async handleSubmit(event) {
        event.preventDefault();
      const {submitted, response} = this.state;
     const { 
       name, 
        email, 
        password1,
        password2, 
      } = this.state;
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
        this.setState({ submitted: true });
        console.log("submitting", submitted );
        console.log("state", this.state);
       
        const password = (password1 == password2) ? password2: null;
        if (name && email && password) {
        register(name, email, password)
        .then(response => {          
                let description = "Thank you! You're successfully registered. Please Login to continue!";
               this.props.onRegister();             
       
        }).catch(error => {
            
               let description = error.message || 'Sorry! Something went wrong. Please try again!';
      
           
        });  
    }
}
    render(){
     
     return (
<Jumbotron className="container"> 
    <Form>
        <h2>Register</h2>
        <FormGroup row>
            <Label for="name" sm={2}>Name</Label>
            <Col sm={10}>
                <Input type="name" name="name" id="fullName" placeholder="Name eg. John Doe"  onChange={e => this.setState({name:e.target.value})} />
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="email" sm={2}>Email</Label>
            <Col sm={10}>
                <Input type="email" name="email" id="email" placeholder="Email"  onChange={e => this.setState({email:e.target.value})} />
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="password1" sm={2}>Password</Label>
            <Col sm={10}>
                <Input type="password" name="password1" id="password1" placeholder="password"   onChange={e => this.setState({password1:e.target.value})}/>
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="password2" sm={2}>Confirm Password</Label>
            <Col sm={10}>
                <Input type="password" name="password2" id="password2" placeholder="confirm password"   onChange={e => this.setState({password2:e.target.value})}/>
            </Col>
        </FormGroup>
        <FormGroup>
            <Col smOffset={2} sm={10}>
                <Button type="submit" onClick={this.handleSubmit} >Register</Button>
            </Col>
        </FormGroup>
    </Form>
</Jumbotron>

        );
    }

    

}