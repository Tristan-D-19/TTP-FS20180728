import React, {Component} from 'react'
import { Alert, Button, Jumbotron,  Form, Label, Input, FormGroup, ControlLabel, HelpBlock, Col } from 'reactstrap';
import { login } from '../utils/APIHelper';
import './Login.css';
import { Link } from 'react-router-dom';
import { ACCESS_TOKEN } from '../constants';




export default class Login extends Component {
    constructor(props) {
    super(props); 
    this.state = {
    email: '',
    password: ''
  }
  this.onSubmit = this.onSubmit.bind(this);
  this.handleInputChange = this.handleInputChange.bind(this);
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
  async onSubmit (event) {
    event.preventDefault()
   const {password, email } = this.state;
   console.log(password);
   console.log(email);

   if (email && password) {
   login(email, password)
    .then(response => {
        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        this.props.onLogin();
        this.props.history.push("/");
    }).catch( error => {
        if(error.status === 401) {
          let description = 'Your Username or Password is incorrect. Please try again!';

                console.log(description);
                           
        } else {               
                let description = 'Sorry! Something went wrong. Please try again!'
                console.log(description);
                
                
          }
      });
  }
}
  render() {
  
    return (
      <Jumbotron className="container">
        <Form onSubmit={this.onSubmit}>
          <h1>Authentication</h1>
          <FormGroup row>
            <Label for="email" sm={2}>Email</Label>
            <Col sm={10}>
                <Input type="email" name="email" id="email" placeholder="Email"  onChange={e => this.setState({email:e.target.value})} />
            </Col>
        </FormGroup>
        <FormGroup row>
            <Label for="password1" sm={2}>Password</Label>
            <Col sm={10}>
                <Input type="password" name="password1" id="password1" placeholder="password"   onChange={e => this.setState({password:e.target.value})}/>
            </Col>
        </FormGroup>
        <Button type="submit" onClick={this.onSubmit} >Login</Button>
        </Form>
      </Jumbotron>
    )
  }
}