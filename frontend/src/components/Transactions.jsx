import React, { Component } from 'react';
import { ListGroup, ListGroupItem, Button, ButtonGroup, Grid, Container, Table, CheckBox, Form, Col, Row, Jumbotron, FormGroup, ControlLabel, Modal, ModalBody, ModalFooter, FormControl, Label} from 'react-bootstrap';
import { getUserTransactions } from '../utils/APIHelper';

//Transaction component: Displays all of a users transactions, namely, stock purchases.
export default class Profile extends Component {

    constructor(props) {
      super(props);
      this.state = {transactions: [], isLoading: true, profile:null, 
      transactionError:{validationState:"", message:""}  
  };
  this.onSubmit = this.onSubmit.bind(this);
      
    }
   
    componentDidMount() {
      this.setState({isLoading: true});
     getUserTransactions()
        .then(response => this.setState({transactions:response, isLoading:false}))
    }
  
  onSubmit(event){
  event.preventDefault()
  

  }
  
    render (){
      let transactions = this.state.transactions;
      let transactionList;
      //map transactions
      if(transactions)
      transactionList = transactions.map(transaction => {
          const type = `${transaction.transactionType || ''}`;
          const price = `${transaction.price || ''}`
          const symbol =  `${transaction.symbol || ''}`;
          const shares =  `${transaction.shares || ''}`;
          return <ListGroupItem>
              {type} ({symbol}) - {shares} @ {price}
          </ListGroupItem>
        });
    
  
  
  return(
  <Grid>
      <h2>Transactions</h2>
      <Row className="show-grid">
          <Col id="user-transactions">
          <ListGroup>
            {transactionList}
        </ListGroup>
          </Col>     
      </Row>
  </Grid>);
    }
  }

