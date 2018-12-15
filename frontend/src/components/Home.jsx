import React, { Component } from 'react';
import './Home.css'
import {  Jumbotron } from 'reactstrap';


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