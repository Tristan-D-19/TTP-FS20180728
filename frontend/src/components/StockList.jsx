import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table, CheckBox, Form, FormGroup, Input, Modal, ModalBody, ModalFooter, ModalHeader, Label} from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';

class StockList extends Component {

  constructor(props) {
    super(props);
    this.state = {stocks: [], isLoading: true, symbol:"", shares:"", purchased:"",  modal: false};
    this.onSubmit = this.onSubmit.bind(this);
    this.toggle = this.toggle.bind(this);
    this.onclick = this.onclick.bind(this);
    this.close = this.close.bind(this);
  }

  toggle(symbol) {   
    console.log(symbol)
    this.setState({
      modal: !this.state.modal,
      symbol:symbol
    });
  
   
  }

  close(){
    this.setState({
      modal: !this.state.modal,
  })
}
  onclick(symbol){ 
   
    console.log("WTF", symbol);
    this.setState({symbol:symbol})
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/stocks/all')
      .then(response => response.json())
      .then(data => this.setState({stocks: data, isLoading: false}));
  }
  async onSubmit (event) {
    event.preventDefault()
    console.log("submit")
  const {symbol, shares, purchased} = this.state;
  const volume = shares;
   if (symbol && shares) {
   const response = await fetch(`/api/stocks/buy`, {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify( {symbol:symbol, volume:volume}),
          }).then(() => {
console.debug(response);
          });
    }

  }

  render() {
    const {stocks, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }
    const stockList = stocks.map(stock => {
      const price = `${stock.lastSalePrice || ''}`;
      const volume = `${stock.volume || ''}`
      const symbol =  `${stock.symbol || ''}`;
      return <tr  key={stock.symbol}>
        <td style={{whiteSpace: 'nowrap'}}>{symbol}</td>
        <td>{price}</td>
        <td>{volume}</td>
        <td>
        <Button size="sm" color="primary" type="button" id={symbol} onClick={this.toggle.bind(this, symbol)}  >buy</Button>
          <Form onSubmit={this.onSubmit}>            
             <Modal isOpen={this.state.modal} toggle={this.toggle} className="modal-display">
          <ModalHeader toggle={this.toggle}>Buy Shares</ModalHeader>  
           
          <Input type="hidden" name="symbol" className="symbol-button" value={this.state.symbol}></Input>
        
          <ModalBody>
          <Label for="shares" className="mt-4" sm={2}>Purchase Shares of {this.state.symbol}</Label> 
         
            <Input type="text" name="volume" id="shares" placeholder="Shares" value={this.state.shares}  onChange={e => this.setState({shares:e.target.value})}/>
           
          </ModalBody>
          <ModalFooter>          
            <Button color="primary"  type="submit" onClick={this.onSubmit} >Purchase</Button>
            <Button color="secondary" type="button" onClick={this.close}>Cancel</Button>
          </ModalFooter>
        </Modal>
           </Form>
        </td>
      </tr>
    });

    return (
      <div>
      
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/stocks">buy Stock</Button>
          </div>
          <h3>My Stocks</h3>
          <Table hover className="mt-4">
            <thead>
            <tr>
              <th width="20%">Symbol</th>
              <th width="20%">Price</th>
              <th>Shares</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {stockList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default StockList;