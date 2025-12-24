package model;

import java.time.LocalDate;

public class Menu {
    private LocalDate date;
    private String repas;

    public Menu(LocalDate date, String repas) {
        this.date = date;
        this.repas = repas;
    }

    public LocalDate getDate() { 
    	return date; 
    	}
    
    public void setDate(LocalDate date) { 
    	this.date = date; 
    	}

    public String getRepas() { 
    	return repas; 
    	}
    
    public void setRepas(String repas) { 
    	this.repas = repas;
    	}

	@Override
	public String toString() {
		return "Menu [date=" + date + ", repas=" + repas + "]";
	}
    
}

