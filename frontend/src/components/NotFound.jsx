import React, { Component } from 'react';
import './NotFound.css';
import { Link } from 'react-router-dom';
import { Button, Jumbotron, Alert } from 'reactstrap';

//Notfound component: displays when a user enters an unknown page
class NotFound extends Component {
    render() {
        return (
        <Jumbotron>
          <div className="page-not-found">
                <h1 className="title">
                    404
                </h1>
                <div className="desc">
                <Alert color="danger">
        The Page you're looking for was not found.
      </Alert>
             </div>
                <Link to="/"><Button className="go-back-btn" type="primary" size="large">Go Back</Button></Link>
            </div>
        </Jumbotron>
         
        );
    }
}
export default NotFound;