import React, { Component } from 'react';
import './Home.css'
import {  Jumbotron } from 'reactstrap';

//Home component to display welcome page to the user
class Home extends Component {

  render() {
    return (
      <div className="Home"> 
        <Jumbotron> Welcome to the stock trade! </Jumbotron>
      </div>   
    );
  }
}

export default Home;