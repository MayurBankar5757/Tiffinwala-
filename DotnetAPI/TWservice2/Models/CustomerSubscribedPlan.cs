using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace TWservice2.Models
{
    public partial class CustomerSubscribedPlan
    {
        public CustomerSubscribedPlan()
        {
            CustomerSubscriptionsJunctions = new HashSet<CustomerSubscriptionsJunction>();
            Feedbacks = new HashSet<Feedback>();
        }

        public int CustomerPlanId { get; set; }
        public int Uid { get; set; }
        public int VSubscriptionId { get; set; }

        [JsonIgnore]
        public DateOnly? StartDate { get; set; }
        [JsonIgnore]
        public DateOnly? EndDate { get; set; }
        [JsonIgnore]
        public DateOnly? OrderedDate { get; set; }

        [JsonPropertyName("startDate")]
        public string? StartDateString => StartDate?.ToString("yyyy-MM-dd");

        [JsonPropertyName("endDate")]
        public string? EndDateString => EndDate?.ToString("yyyy-MM-dd");

        [JsonPropertyName("orderedDate")]
        public string? OrderedDateString => OrderedDate?.ToString("yyyy-MM-dd");

        public virtual User UidNavigation { get; set; } = null!;
        public virtual VendorSubscriptionPlan VSubscription { get; set; } = null!;
        public virtual ICollection<CustomerSubscriptionsJunction> CustomerSubscriptionsJunctions { get; set; }
        public virtual ICollection<Feedback> Feedbacks { get; set; }
    }
}
